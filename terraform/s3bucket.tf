resource "aws_s3_bucket" "social-network-bucket" {
  bucket = "social-network-bucket-${var.environment}"

  tags = {
    Name        = "social-network-bucket"
    Environment = var.environment
  }
}
