# BOCRA Hackathon Backend - Deployment Guide

## Overview
This guide walks you through setting up Docker containerization and deploying to Railway.com with automated GitHub Actions.

## Prerequisites
- Docker and Docker Compose installed locally
- GitHub account with repository access
- Railway.com account
- Docker Hub account (for pushing images)

---

## Step 1: Local Testing with Docker Compose

### Build and Run Locally
```bash
# Clone the repository
git clone <your-repo-url>
cd Bocra-Hackathon--backend-leth

# Build and start services (MySQL + App)
docker-compose up --build

# Access the application
# App: http://localhost:8083
# MySQL: localhost:3306 (root/root)
```

### Verify Application Health
```bash
# Check if app is running
curl http://localhost:8083/actuator/health

# View logs
docker-compose logs -f app
docker-compose logs -f mysql
```

### Stop Services
```bash
docker-compose down

# To remove volumes as well
docker-compose down -v
```

---

## Step 2: Push to GitHub Repository

### Initialize Git (if not already done)
```bash
git init
git add .
git commit -m "Add Docker configuration and deployment files"
git branch -M main
git remote add origin https://github.com/<your-username>/<repo-name>.git
git push -u origin main
```

---

## Step 3: Set Up GitHub Secrets

Go to your GitHub repository → Settings → Secrets and variables → Actions

Add the following secrets:

### For Docker Hub (Optional, if pushing to Docker Hub)
- `DOCKER_USERNAME`: Your Docker Hub username
- `DOCKER_PASSWORD`: Your Docker Hub personal access token

### For Railway (Required)
- `RAILWAY_TOKEN`: Your Railway API token
  - [Get Railway Token](https://railway.app/account/tokens)

### For Application
- `MYSQL_ROOT_PASSWORD`: MySQL root password (e.g., `railway_root_pass`)
- `MYSQL_PASSWORD`: MySQL user password (e.g., `railway_app_pass`)
- `JWT_SECRET`: Your JWT secret key (generate a strong one)
- `CORS_ALLOWED_ORIGINS`: Frontend URL (e.g., `https://yourdomain.com`)

---

## Step 4: Set Up Railway Project

### Option A: Connect to GitHub (Recommended)
1. Log in to [Railway.com](https://railway.app)
2. Create a new project
3. Select "Deploy from GitHub"
4. Connect your GitHub account and select this repository
5. Railway will automatically detect the Dockerfile and build it

### Option B: Manual Setup
1. Create a new Railway project
2. Add MySQL service:
   - Click "Add Service" → "Database" → Select "MySQL"
   - Railway will create a MySQL instance automatically
3. Add your backend service:
   - Click "Add Service" → "GitHub Repo"
   - Select this repository
4. Configure environment variables (see below)

---

## Step 5: Configure Environment Variables in Railway

In your Railway project, set these variables for the backend service:

```
SPRING_DATASOURCE_URL=jdbc:mysql://<MYSQL_HOST>:3306/bocra_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=<MYSQL_ROOT_PASSWORD>
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SERVER_PORT=8083
APP_JWT_SECRET=<YOUR_SECURE_JWT_SECRET>
APP_CORS_ALLOWED_ORIGINS=<YOUR_FRONTEND_URL>
```

### Get MySQL Connection Details
In Railway:
1. Go to your MySQL service
2. Click on "Variables" tab
3. Copy the connection string or individual variables:
   - `MYSQL_HOST`: The container name or hostname
   - `MYSQL_PORT`: Usually 3306
   - `MYSQL_DATABASE`: bocra_db
   - `MYSQL_ROOT_PASSWORD`: Set during setup
   - `MYSQL_USER`: bocra_user
   - `MYSQL_PASSWORD`: Set during setup

### Link Services
1. In your backend service, add the MySQL service reference:
   ```
   SPRING_DATASOURCE_URL=jdbc:mysql://${{ MySQL.RAILWAY_MYSQL_HOST }}:${{ MySQL.RAILWAY_MYSQL_PORT }}/bocra_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   ```

---

## Step 6: Configure GitHub Actions Workflow

The workflow file `.github/workflows/deploy-to-railway.yml` is already set up to:

1. ✅ Checkout code on push to main/develop
2. ✅ Set up Java 17 and Maven
3. ✅ Build the project
4. ✅ Build Docker image
5. ✅ Push to Docker Hub (if credentials provided)
6. ✅ Trigger Railway deployment

### Workflow Triggers
The workflow runs automatically when:
- You push to `main`, `master`, or `develop` branches
- You create a pull request to `main` or `master`

### Monitor Deployments
- GitHub: Go to "Actions" tab to see workflow status
- Railway: Check your project's "Deployments" tab for real-time logs

---

## Step 7: Set Up Continuous Deployment

### GitHub Auto-Deployment to Railway
Railway automatically deploys when:
- Connected to GitHub and detects new commits
- A new Docker image is pushed (if using Docker Hub)

### For Automatic Deployments:
1. In Railway project settings, enable "Auto-Deploy"
2. Select the branch to deploy from (default: main)
3. Every push to that branch will trigger a deployment

---

## Step 8: Verify Deployment

Once deployed to Railway:

### Check Application Health
```bash
# Replace <railway-url> with your Railway domain
curl https://<railway-url>/actuator/health
```

### View Live Logs
- Go to Railway project → Backend service → "Logs" tab
- Check for any errors or startup issues

### Database Migration
- JPA Hibernate will automatically create/update tables on first run
- Monitor logs to ensure `ddl-auto: update` completes successfully

---

## Troubleshooting

### Issue: Docker Build Fails
```bash
# Test build locally first
docker build -t bocra-backend:test .

# If it fails, check:
# 1. Java 17 is available
# 2. All Maven dependencies are resolvable
# 3. Maven cache is cleared: mvn clean
```

### Issue: MySQL Connection Error
- Verify `SPRING_DATASOURCE_URL` contains correct host and port
- Check MySQL credentials match environment variables
- In Railway, ensure MySQL service is running before backend
- Add `allowPublicKeyRetrieval=true` to JDBC URL

### Issue: JWT Validation Fails
- Ensure `APP_JWT_SECRET` is the same in all environments
- Regenerate tokens after secret changes

### Issue: CORS Errors
- Update `APP_CORS_ALLOWED_ORIGINS` to match your frontend URL
- For multiple origins: `https://domain1.com,https://domain2.com`

### View Detailed Logs
```bash
# Locally
docker-compose logs --tail=100 app

# On Railway - use web dashboard or Railway CLI
railway logs -s backend
```

---

## Security Best Practices

1. ✅ Use strong JWT secret (Railway secrets, not in code)
2. ✅ Use environment variables for all sensitive data
3. ✅ MySQL root password should be 16+ characters
4. ✅ Restrict CORS to your actual frontend domain
5. ✅ Use MySQL user with limited privileges (not root) in production
6. ✅ Enable HTTPS (Railway provides automatic SSL)
7. ✅ Rotate JWT secret periodically
8. ✅ Keep dependencies updated

---

## Production Checklist

- [ ] Change `ddl-auto` to `validate` in production
- [ ] Increase JPA connection pool size
- [ ] Set up monitoring and alerting
- [ ] Enable database backups on Railway
- [ ] Use strong, unique passwords
- [ ] Set appropriate log levels
- [ ] Test database failover procedures
- [ ] Configure rate limiting on endpoints
- [ ] Set up CI/CD notifications

---

## Useful Commands

### Local Development
```bash
# Build Docker image
docker build -t bocra-backend:latest .

# Run with MySQL
docker-compose up -d

# Stop all services
docker-compose stop

# View logs
docker-compose logs -f app

# Execute commands in container
docker exec -it bocra_backend /bin/sh
```

### Railway CLI
```bash
# Install Railway CLI
npm i @railway/cli -g

# Login to Railway
railway login

# Deploy
railway up

# View logs
railway logs

# Link local repository to Railway project
railway link <project-id>
```

---

## Reference Links

- [Railway Documentation](https://docs.railway.app)
- [Spring Boot Docker Guide](https://spring.io/guides/topical/spring-boot-docker)
- [MySQL Docker Hub](https://hub.docker.com/_/mysql)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

---

## Support

For issues:
1. Check the troubleshooting section
2. Review Railway and GitHub Actions logs
3. Check Docker Compose locally for misconfigurations
4. Verify all environment variables are set correctly

