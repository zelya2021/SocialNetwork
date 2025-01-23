# Social Network API Documentation

## Completed Tasks

### Social network features

- [x] **Create Gitlab repository**
- [x] **User Registration** `POST /auth/register`
  - firstName: string
  - lastName: string
  - email: string
  - password: string
- [x] **User Login** `POST /auth/login` returns JWT token
  - email: string
  - password: string
- [x] **Password Reset Initiation** `POST /auth/forgot-password`
  - email: string
- [x] **Password Reset** `POST /auth/reset-password`
  - hash: string
  - newPassword: string
- [x] **Change Password** `POST /auth/change-password`
  - oldPassword: string
  - newPassword: string
- [x] **Get Current User** `GET /users/me`
- [x] **Update Current User** `PUT /users/me`
  - firstName: string
  - lastName: string
  - email: string
- [x] **Get User by ID** `GET /users/:id`
- [x] **List Users with Pagination** `GET /users?page=1&limit=50`
- [x] **Send Friendship Request** `POST /friend-request`
  - userId: string | number;
- [x] **List Incoming Friendship Requests** `GET /friendship-requests/incoming`
- [x] **Accept Friendship Request** `POST /friend-request/:id/accept`
- [x] **Decline Friendship Request** `POST /friend-request/:id/decline`
- [x] **List Friends** `GET /friends`
- [x] **Remove Friend** `DELETE /friends/:userId`

### Chats and Messages

- [x] **Create Direct Chat** `POST /chat/direct`
  - userId: string
- [x] **Create Group Chat** `POST /chat/group`
  - name: string
  - membersIds: array of strings
- [x] **Update Group Chat** `PUT /chat/group/:id`
  - name: string
  - membersIds: array of strings
- [x] **Delete Chat** `DELETE /chat/:id`
- [x] **Get Chat by ID** `GET /chat/:id`
- [x] **List Chats for Current User** `GET /chat?page=1&limit=20`
- [x] **Send Message** `POST /message`
  - chatId: string
  - message: string
  - authorId: string
  - time: datetime
- [x] **Update Message** `PUT /message/{id}`
  - message: string
  - chatId: string
- [x] **List Messages in Conversation** `GET /messages?chatId={id}&page=1&limit=50`

### Infrastructure and Configuration

- [x] **AWS Secrets Creation** for storing credentials
- [x] **SpringBoot Actuator Configuration** for healthchecks
- [x] **Docker Image Build Configuration** using buildpack
- [x] **Terraform Project** for AWS resources like PostgreSQL and S3
- [x] **Redis Caching Configuration**
- [x] **Springdoc OpenAPI Configuration** for auto-generated API docs

## In Progress Tasks

### Notifications — message notifications management

- [ ] **List Notifications** `GET /notifications?status={value}`
- [ ] **Accept Notifications** `PUT /notifications`

### Files — management of uploaded files to S3 bucket

- [ ] **Upload File** `POST /file`
- [ ] **List Files** `GET /file?page=1&limit=30`
- [ ] **Get File by ID** `GET /file/{id}`

### Posts and Comments

- [ ] **List Posts** `GET /post`
- [ ] **Create Post** `POST /post`
- [ ] **Update Post** `PUT /post/{id}`
- [ ] **Delete Post** `DELETE /post/{id}`
- [ ] **Like Post** `PUT /post/{id}/like`
- [ ] **List Comments** `GET /comments?postId={id}&page=1&limit=30`
- [ ] **Create Comment** `POST /comments`
- [ ] **Update Comment** `PUT /comments/{id}`
- [ ] **Delete Comment** `DELETE /comments/{id}`

### Advanced Configuration and Deployment

- [ ] **Enable Full-text Search for Posts Content**
  - Provision Amazon Opensearch service using Terraform
  - Index all created posts
  - Index all sent messages
- [ ] **Kubernetes Deployment**
  - Implement `Deployment` with readiness and liveness probes
  - Implement `Service` of type `ClusterIP`
  - Implement `ExternalSecrets` for managing secrets
  - Implement `HorizontalPodAutoscaler` for scaling
  - Implement `Ingress` for routing and load balancing
- [ ] **Gitlab CI/CD Pipeline Configuration**
  - Setup CI/CD with AWS credentials for targeted deployments
  - Build docker image and push to AWS ECR
  - Apply Terraform configurations
  - Deploy updates to the dev and prod environments
  - Implement version tagging and release management
- [ ] **Websockets Integration**
  - Install necessary dependencies
  - Authenticate via JWT in Websockets connection
  - Emit notifications through socket events when relevant
