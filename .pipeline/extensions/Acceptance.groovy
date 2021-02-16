void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"

    try {
        git credentialsId: githubCredentialsId,
            url: 'git@github.com:T-Bendrath/openSAP-CAP-Pipeline.git',
            branch: 'main'

        // Setup the environment to start the application with CAP server and execute UIVeri5 system tests
        uiVeri5ExecuteTests script: this
        echo "Finished UIVeri5Test exeuction"

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