resource "aws_elasticache_cluster" "social_network_redis_cluster" {
  cluster_id           = "social_network-redis-cluster"
  engine               = "redis"
  node_type            = "cache.t2.micro"
  num_cache_nodes      = 1
  parameter_group_name = "default.redis6.x"
  engine_version       = "6.x"
  port                 = 6379
  security_group_ids  = [aws_security_group.social_network_redis_sg.id]
  subnet_group_name   = aws_elasticache_subnet_group.subnet_group.name
}

resource "aws_security_group" "social_network_redis_sg" {
  name        = "social-network-redis-sg"
  description = "Allow inbound traffic"

  ingress {
    from_port   = 6379
    to_port     = 6379
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_elasticache_subnet_group" "subnet_group" {
  name       = "social_network-subnet-group"
  subnet_ids = ["subnet-12345678", "subnet-87654321"]
}

