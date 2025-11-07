
docker-compose up -d mysql-standalone
docker-compose up -d mysql-standalone
done
# CI/CD and Infrastructure — CafeManagement

This document summarizes the CI/CD pipelines (Jenkins & GitHub Actions), Docker Compose usage (MySQL), test guidance, and Terraform/EKS notes for the CafeManagement repository.

Keep this file in the repo root to help contributors run local builds and understand the CI/CD contract.

## Snapshot

- Backend: Spring Boot (Java 17). Application runtime uses MySQL (`mysql-standalone` service in `cafemanagement/docker-compose.yml`).
- Unit tests: Use H2 in-memory DB by default (`src/test/resources/application-test.properties`) so `mvn test` runs without MySQL.
- Docker Compose: `cafemanagement/docker-compose.yml` brings up MySQL and the backend for local development.
- CI: Jenkinsfiles for backend/frontend and GitHub Actions workflow at `.github/workflows/cafe-management-cicd.yml` to build/push images and deploy with Helm to EKS.
- Infra: Terraform and helper scripts live under `CICD/CICD/terraform` and `CICD/CICD/scripts`.

## Where to look

- `cafemanagement/docker-compose.yml` — compose file for local dev (services: `mysql-standalone`, `springboot-docker-container`).
- `cafemanagement/db/Dockerfile` — base image `mysql:latest` and SQL init files (if any).
- `cafemanagement/jenkinfile` — Jenkins pipeline for backend (build, docker-compose build/push, deploy steps).
- `Frontend/jenkinfile` — Jenkins pipeline for frontend (docker-compose build/push).
- `.github/workflows/cafe-management-cicd.yml` — GitHub Actions: tests, build/push images to registry, Helm deploy to EKS.

## Docker Compose and MySQL

The compose file defines:

- `mysql-standalone` — builds from `cafemanagement/db/Dockerfile` which is based on `mysql:latest`.
- `springboot-docker-container` — backend app; `depends_on: - mysql-standalone` and environment variables configured to connect to `mysql-standalone`.

application.properties (runtime) points to:

```
spring.datasource.url=jdbc:mysql://mysql-standalone/cafemanagement?allowPublicKeyRetrieval=true&useSSL=false
```

This is correct for compose-based runs. If you run the app locally without compose, update the URL to point to your local DB or run the compose MySQL first.

Quick compose commands:

```bash
# bring up mysql for local dev
cd cafemanagement
docker-compose up -d mysql-standalone

# bring up both mysql and app
docker-compose up -d

# stop and remove
docker-compose down
```

Service names used for CI/Helm:
- Backend service name in compose should be `springboot-docker-container` (used locally); in CI/images we tag repository names `cafe-backend` and `cafe-frontend`.

## Unit tests and CI guidance

- Unit tests in `cafemanagement/src/test` use H2 by default (see `src/test/resources/application-test.properties`). This keeps CI fast and deterministic.
- If you need integration tests against MySQL, prefer Testcontainers — it starts a disposable DB during tests and avoids a shared DB dependency.
- Alternatively, add a CI step to boot `mysql-standalone` via `docker-compose up -d` and wait for it to be ready before running `mvn test`.

Example CI snippet to start MySQL and run tests:

```bash
cd cafemanagement
docker-compose up -d mysql-standalone

# wait for mysql to accept connections (example)
for i in $(seq 1 20); do
  if mysqladmin ping -h "127.0.0.1" --silent; then
    break
  fi
  sleep 3
done


```

Notes: Use `mysqladmin` (from MySQL client) or `docker exec` + script to check readiness.

## Jenkins pipelines (summary)

- `cafemanagement/jenkinfile` runs:
  - Maven build (skip tests in pipeline build stage by default: `mvn clean install -DskipTests`)
  - `docker-compose -f cafemanagement/docker-compose.yml build` and `push` (requires Docker and Docker Compose on agents)
  - Deploy step (ECS/EKS) which uses AWS credentials configured in Jenkins

- `Frontend/jenkinfile` runs similar docker-compose build/push for frontend.

Make sure Jenkins agents have Docker CLI and Docker Compose installed, and credentials stored in Jenkins for Docker Hub or ECR.

## GitHub Actions CI (summary)

- Workflow location: `.github/workflows/cafe-management-cicd.yml`.
- High-level steps:
  1. Backend tests (uses `./cafemanagement` working dir)
  2. Frontend tests (uses `./Frontend` working dir)
  3. Build & push backend image to registry (tags: `${{ github.run_number }}`, `latest`)
  4. Build & push frontend image to registry
  5. Deploy to EKS with Helm (overrides image repo/tag)

Required GitHub secrets (minimum):

- `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` — for ECR push and `aws eks update-kubeconfig`.
- `REGISTRY` — full ECR registry URL (example: `123456789012.dkr.ecr.us-east-1.amazonaws.com`) used in the current workflow.
- `SONAR_TOKEN`, `BACKEND_SONAR_PROJECT_KEY`, `FRONTEND_SONAR_PROJECT_KEY` — optional for SonarCloud steps.

If you prefer docker-compose builds in GitHub Actions (instead of `docker build`), update the backend and frontend build steps to run `docker-compose -f cafemanagement/docker-compose.yml build` and `docker-compose -f Frontend/docker-compose.yml build` respectively. I can change this for you.

## Terraform / EKS notes

- Terraform files and eksctl/terraform helper scripts are in `CICD/CICD/terraform` and `CICD/CICD/scripts`.
- The GitHub Actions deploy step assumes the EKS cluster exists and the runner has permission to update kubeconfig. CI uses AWS credentials to authenticate.

## Recommended small improvements I can implement now

1. Add a helper script to `cafemanagement/script/start-mysql-and-test.sh` that:
   - brings up `mysql-standalone` via docker-compose
   - waits until MySQL is ready
   - runs `mvn test` (or `mvn -DskipITs=false verify` for integration tests)

2. Update GitHub Actions to use `docker-compose` for builds (if you prefer consistency with Jenkins/local dev).

3. Add Testcontainers to `pom.xml` for an integration test profile (optional, safer for CI).

If you want, pick one of the improvements and I will implement it and update the workflow accordingly.
