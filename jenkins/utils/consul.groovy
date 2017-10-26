import groovy.json.JsonSlurperClassic
import groovy.json.JsonOutput

consul_endpoint = 'http://34.214.176.16:8500'

def getConsulValue(def key) {
  def command = "curl ${consul_endpoint}/v1/kv/${key}?recurse&token=${master_token}"
  try {
      def jsonOutput = command.execute().text
      def value = new String(new JsonSlurperClassic().parseText(jsonOutput)[0]['Value'].decodeBase64())
      return value
  } catch(Exception e) {
      return null
  }
}

def putConsulValue(def key, def value) {
  command = "curl -X PUT -d \"${value}\" ${consul_endpoint}/v1/kv/${key}?token=${master_token} 2>/dev/null"
  sh command
}

return this
