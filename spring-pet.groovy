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
    stage("Install git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE git clone https://github.com/spring-projects/spring-petclinic.git"
        }
    stage("Install git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE 'cd spring-petclinic/ && ./mvnw package'"
        }
    stage("Install git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE 'cd spring-petclinic &&  mv target/*.jar target/app.jar'"
        }
    stage("Install git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE java -jar spring-petclinic/target/app.jar"
        }
    }
}