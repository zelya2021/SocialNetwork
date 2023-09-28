# Functional requirements
- Auth — authentication operations
    - User POST /auth/register
    - Login existing user POST /auth/login, returns the JWT token
    - Send email with reset password hash  /auth/forgot-password
    - Reset password to new password /auth/reset-password
    - Change password for existing user — /auth/change-password
