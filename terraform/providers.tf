provider "aws" {
  region = "us-west-2"
}

terraform {
  backend "s3" {
    bucket = "social-network-terraform-state-bucket"
    key = "dev/terraform.tfstate"
    region = "us-west-2"
  }
}