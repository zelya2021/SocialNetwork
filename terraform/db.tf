data "aws_secretsmanager_secret" "social-network-secret" {
  name = "social-network-secret-dev"
}

data "aws_secretsmanager_secret_version" "social-network-secret" {
  secret_id = data.aws_secretsmanager_secret.social-network-secret.id
}

resource "aws_db_instance" "postgres_db" {
  identifier = "social-network-${var.environment}"
  allocated_storage    = 20
  storage_type         = "gp2"
  engine               = "postgres"
  engine_version       = "16.2"
  instance_class       = "db.t3.micro"
  db_name              = "social_network"
  username             = jsondecode(data.aws_secretsmanager_secret_version.social-network-secret.secret_string)["DB_USERNAME"]
  password             = jsondecode(data.aws_secretsmanager_secret_version.social-network-secret.secret_string)["DB_PASSWORD"]
  parameter_group_name = "default.postgres16"
  db_subnet_group_name = var.db_subnet_group_name
  vpc_security_group_ids = [aws_security_group.postgres_sg.id]
  skip_final_snapshot  = true
  publicly_accessible  = true
}

output "db_endpoint" {
  value = aws_db_instance.postgres_db.endpoint
}

resource "aws_security_group" "postgres_sg" {
  name = "social_network_db_sg"
  description = "Security Group for RDS PostgresSQL"
  vpc_id = var.vpc_id

  ingress {
    from_port = 5432
    protocol  = "tcp"
    to_port   = 5432
    cidr_blocks = ["0.0.0.0/0"]
  }
}

