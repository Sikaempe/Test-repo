pipeline {
    agent any

    environment {
        BITBUCKET_URL = 'https://bitbucket.e-konzern.de/scm/btcvpp/test-repo.git'
        GITHUB_REPO_URL = 'github.com/Sikaempe/test-repo.git'
    }

    stages {
        stage('Add GitHub Remote') {
            steps {
                
                script {
                    echo "Adding GitHub remote..."
                    sh """
                        git remote add github https://$GITHUB_REPO_URL
                    """
                }
                
            }
        }

        stage('Push All Branches to GitHub') {
            steps {
                script {
                    echo "Pushing all branches to GitHub..."
                    sh """
                        git push github --all
                    """
                }
            }
        }

        stage('Push All Tags to GitHub') {
            steps {
                script {
                    echo "Pushing all tags to GitHub..."
                    sh """
                        git push github --tags
                    """
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
