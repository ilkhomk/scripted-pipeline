  
properties([
    parameters([
        string(defaultValue: '', description: 'Provide node IP', name: 'node', trim: true)
        ])
    ])

node{
    stage("Pull Repo"){
        git url: 'https://github.com/ilkhomk/ansible-spring.git'
    }
    stage("Install Prerequisites"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'prerequisites.yml'
    }
    withEnv(['SPRING_REPO=https://github.com/ikambarov/spring-petclinic.git', 'SPRING_BRANCH=master']) {
        stage("Pull Repo"){
            ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'pull_repo.yml'
        }
    }
    stage("Install Java"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'install_java.yml'
    } 
    stage("Install Maven"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'install_maven.yml'
    } 
    stage("Start App"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'start_app.yml'
    } 
}