variable "vpc_id" {
  type = string
  default = "vpc-09320271"
}

variable "db_subnet_group_name" {
  type        = string
  default = "default"
}

variable "environment" {
  type = string
  default = "dev"
}