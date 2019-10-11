# Configuring AWS Architecture

## First Time Execution

`aws cloudformation create-stack --stack-name core-nlp --template-body file://template.yaml --capabilities CAPABILITY_IAM`

## Updating Configuration

`aws cloudformation deploy --template-file template.yaml --stack-name core-nlp --capabilities CAPABILITY_NAMED_IAM`

Creates the following resources:
- IAM Role and Profile for CLoudformation and EC2 instances
- AUto-scaling Launch configuration for dynamically-spawned EC2 instances
- Elastic Load BalancerV2 pointing
- S3 bucket for Elastic Load BalancerV2 Access Logs
- TargetGroup linking Load BalancerV2 to Auto Scaling Group
- Auto Scaling Group


# Deploying Code Changes

There is no CodeDeploy for CoreNLP. As the application is not subject to change, all required configurations and code are packaged in the AMI.

If the source code needs to be updated, a new AMI has to be created and linked to template.yaml.