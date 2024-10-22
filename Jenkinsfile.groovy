pipeline {
    agent { label 'linux&&azure' }

    environment {
        BITBUCKET_URL = 'https://bitbucket.e-konzern.de/scm/btcvpp/test-repo.git'
        GITHUB_REPO_URL = 'https://github.com/Sikaempe/test-repo.git'
    }

    stages {
        stage('Clone Bitbucket Repository') {
            steps {
                script {
                    echo "Cloning Bitbucket repository..."
                    withCredentials([usernamePassword(credentialsId: 'scm-default', passwordVariable: 'BITBUCKET_PASS', usernameVariable: 'BITBUCKET_USER')]) {
                        sh """
                            if [ ! -d "test-repo" ]; then
                                git clone https://$BITBUCKET_USER:$BITBUCKET_PASS@bitbucket.e-konzern.de/scm/btcvpp/test-repo.git test-repo
                            else
                                echo "Repository already cloned."
                            fi
                        """
                    }
                }
            }
        }

        stage('Add GitHub Remote') {
            steps {
                script {
                    echo "Adding GitHub remote..."
                    withCredentials([string(credentialsId: 'github-token-simon', variable: 'GITHUB_TOKEN')]) {
                        sh """
                            if git remote | grep -q github; then
                                git remote add --fetch https://${GITHUB_TOKEN}@github.com/Sikaempe/test-repo.git
                            fi
                        """
                    }
                }
            }
        }

        stage('Push All Branches to GitHub') {
            steps {
                script {
                    echo "Pushing all branches to GitHub..."
                    withCredentials([string(credentialsId: 'github-token-simon', variable: 'GITHUB_TOKEN')]) {
                        sh """
                            git push https://${GITHUB_TOKEN}@github.com/Sikaempe/test-repo.git --all
                        """
                    }
                }
            }
        }

        stage('Push All Tags to GitHub') {
            steps {
                script {
                    echo "Pushing all tags to GitHub..."
                    withCredentials([string(credentialsId: 'github-token-simon', variable: 'GITHUB_TOKEN')]) {
                        sh """
                            git push https://${GITHUB_TOKEN}@github.com/Sikaempe/test-repo.git --tags
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Bitbucket repository successfully synced to GitHub."
        }
        failure {
            echo "Failed to sync Bitbucket repository to GitHub."
        }
    }
}
