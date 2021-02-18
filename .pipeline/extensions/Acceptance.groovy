void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"

    // Execute original stage as defined in the template
    // params.originalStage()

    try {

        // Clone the git repository and execute UIVeri5 tests
        // git credentialsId: "githubCredentialsId",
        //     url: scm.userRemoteConfigs[0].url,
        //     branch: "main"

        git credentialsId: params.script.commonPipelineEnvironment.gitSshKeyCredentialsId,
            url: scm.userRemoteConfigs[0].url,
            branch: params.script.commonPipelineEnvironment.gitBranch

        uiVeri5ExecuteTests script: this
        
        // Publish test report for UIVeri5 on Jenkins
        publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "./target/report/screenshots",
            reportFiles: "report.html",
            reportName: "UIVeri5 Test Report"
        ]

    } finally {

        deleteDir()
        echo "End - Extension for stage: ${params.stageName}"

    }
}
return this