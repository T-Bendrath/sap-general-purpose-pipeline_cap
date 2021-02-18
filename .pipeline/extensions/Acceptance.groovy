void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"

    // Execute original stage as defined in the template
    // params.originalStage()

    try {

        // Clone the git repository and execute UIVeri5 tests
        git url: scm.userRemoteConfigs[0].url,
            credentialsId: "githubCredentialsId",
            branch: "main"

        uiVeri5ExecuteTests script: this

    } finally {

        // Publish test report for UIVeri5 on Jenkins
        publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "./app/admin/webapp/test/uiveri5/target/report/screenshots",
            reportFiles: "report.html",
            reportName: "UIVeri5 Test Report"
        ]

        deleteDir()
        echo "End - Extension for stage: ${params.stageName}"

    }
}
return this