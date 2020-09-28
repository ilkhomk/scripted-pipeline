properties([
    parameters([
        string(defaultValue: '', description: 'Input node IP', name: 'SSHNODE', trim: true)
        ])
    ])
node {
    withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
    stage("Initialize") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install epel-release -y "
        }
    stage("Install Java") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install java-1.8.0-openjdk-devel -y "
        }
    stage("Install Git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install git -y "
        }
    stage("Install Terraform") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE 'yum install wget unizp -y && wget https://releases.hashicorp.com/terraform/0.13.1/terraform_0.13.1_linux_amd64.zip && unzip terraform_0.13.1_linux_amd64.zip && mv terraform /usr/bin/' "
        }
    }
}