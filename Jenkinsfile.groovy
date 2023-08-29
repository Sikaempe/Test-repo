@Library(["vpp", "bop@auto-channel","bop-release"]) _

@NonCPS
def setupLogging() {
}

    stage('TestGit') 
    {
    util.JenkinsUtils.log = { String msg-> println msg }

        def xChannel = auto_channel()
        
        echo "channel: " + xChannel

        if (xChannel.equals(util.AutoChannel.TESTING)) 
        {
            echo "testing"
        } 
        else if (xChannel.equals(util.AutoChannel.STABLE)) 
        {
            echo "stable"
        } 
        else 
        {
            echo "other"
        }
    }