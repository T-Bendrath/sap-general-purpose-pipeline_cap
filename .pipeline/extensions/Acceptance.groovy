void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"

    // Execute original stage as defined in the template
    // params.originalStage()

    try {
        // Setup the environment to start the application with CAP server and execute UIVeri5 system tests
        git credentialsId: 'githubCredentialsId',
            url: scm.userRemoteConfigs[0].url,
            branch: ${params.script.commonPipelineEnvironment.gitBranch}

        uiVeri5ExecuteTests script: this
        
        echo "Finished UIVeri5Test execution"
    } finally {
        // Publish Test Report for UIVeri5 on Jenkins
        publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "./target/report/screenshots",
            reportFiles: "report.html",
            reportName: "UIVeri5 Test Report"
        ]

        deleteDir()

        echo "End - Extension for stage: ${params.stageName}"
    }
}
return this