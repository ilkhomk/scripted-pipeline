node {
    stage("Initialize") {
       withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@64.225.18.217 yum install epel-release -y "
        }
    }
}