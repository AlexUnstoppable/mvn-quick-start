pipeline {
    agent none 
    environment {
        KITTENS = "furry"
        BANANAS = "great"
    }
    parameters {
        string(name: 'Greeting', defaultValue: 'Hello', description: 'How should I greet the world?')
        choice(name: 'Versions', choices: "3.4\n3.5", description: 'Build for which version')
        choice(name: 'Nodes', choices: "Linux\nWindows", description: 'Choose Node!')
    }
    stages {
        stage("checkout") {
            agent {
                label 'Linux'
            }
            steps {
                echo 'Checkout....'
            }
        }
        stage("pre-build") {
            steps {
                parallel (
                    "Linux" : {
                        node('Linux') {    
                            sh 'echo Checking Retry wrapper'
                            retry(5) {
                                sh 'echo blablabla'
                            }
                            sh 'printenv'
                            sh 'echo checking maven version'
                            sh 'echo "Kittens are ${KITTENS}"'
                            echo "Bananas are ${env.BANANAS}"
                            echo "Login is ${env.LOGIN} and Pass is ${env.PASS}"
                            timeout(time: 3, unit: 'MINUTES'){
                                sh '/opt/apache-maven-3.5.0/bin/mvn --version'
                            }
                        }
                    },
                    "Windows" : {
                        node('Windows') {
                            sh "echo from windows"
                            sh "echo step1"
                            sh "echo step2"
                        }
                    }
                )
            }
            post {
                always {
                    node('Linux') {
                        sh "echo Pre-Build from post-always"
                    }
                }
                failure {
                    node('Linux') {
                        sh "echo from Pre-Build post-failure"
                        slackSend message: "The pipeline ${currentBuild.fullDisplayName} completed successfully."
                    }    
                }
                success {
                    node('Linux') {
                        sh "echo from post-success"
                    
                        mail(from: "bob@example.com", 
                        to: "steve@example.com", 
                        subject: "That build passed.",
                        body: "Nothing to see here")
                        
                        slackSend( 
                            message: "The pipeline ${currentBuild.fullDisplayName} completed successfully."
                        )
                    }
                }
            }
        }   
        stage("Build") {
            steps {
                echo 'Building ....'
                sh 'make'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
            post {
                always {
                    node('Linux') {
                        sh "echo from Build post-always"
                        sh "Storing artifacts"
                        archive 'build/libs/**/*.jar'
                        sh "Storing testing results"
                        junit 'build/reports/**/*.xml'
                    }
                }
                failure {
                    node('Linux') {
                        sh "echo from post-failure"
                        slackSend message: "The Build ${currentBuild.fullDisplayName} not completed - failure."
                    }    
                }
                success {
                    node('Linux') {
                        sh "echo from Build post-success"
                    
                        mail(from: "bob@example.com", 
                        to: "steve@example.com", 
                        subject: "That build passed.",
                        body: "Nothing to see here")
                        
                        slackSend( 
                            message: "The Build ${currentBuild.fullDisplayName} completed successfully."
                        )
                    }
                }
            }
            
        }
        stage("Testing: Unit") {
            steps {
                echo 'JUnit Testing ....'
            }
        }
        stage("test: integration-&-quality") {
            steps {
                echo 'Integrating & Quality Testing ....'
            }
        }
        stage("test: functional") {
            steps {
                echo 'Functional Testing....'
            }
        }
        stage("test: load-&-security") {
            steps {
                echo 'Load & Security Testing....'
            }
        }
        stage("approval") {
            steps {
                echo 'Approval....'
            }
        }
        stage("deploy: staging") {
            when {
			  expression {
				currentBuild.result == null || currentBuild.result == 'SUCCESS' 
			  }
            }
            steps {
                echo 'Deploying....'
            }
        }
    }
    
}
