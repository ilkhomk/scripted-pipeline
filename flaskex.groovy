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
    stage("Install Python") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install python3 -y "
        }
    stage("Install Git") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install git -y "
        }
    stage("Git Clone") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE git clone https://github.com/anfederico/Flaskex"
        }
    stage("Pip install") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE 'cd Flaskex && pip3 install -r requirements.txt ' "
        }      
    stage("Python") {
        sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE python3 Flaskex/app.py "
        }            
    }
}