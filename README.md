# Functional requirements
**Auth — authentication operations**
- Auth — authentication operations
- User POST /auth/register
- Login existing user POST /auth/login, returns the JWT token
- Send email with reset password hash  /auth/forgot-password
- Reset password to new password /auth/reset-password
- Change password for existing user — /auth/change-password

**Users — users management**

- Get currently logged in user by JWT token GET /users/me
- Update profile of currently logged in users PUT /users/me
- Get any user by id: GET /users/:id
- Get list of users with pagination GET /users?page=1&limit=50

**Friendship requests — the outgoing and incoming requests for friendship**

- Send friendship request POST /friend-request
- Get incoming friendship requests for currently logged in user by JWT token GET /friendship-requests/incoming
- Accept incoming friendship requests POST /friend-request/:id/accept
- Decline incoming friendship requests POST /friend-request/:id/decline

