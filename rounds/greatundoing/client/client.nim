import std/[httpclient, json]

let client = newHttpClient()
client.headers = newHttpHeaders({ "Content-Type": "application/json" })
let body = %*{
    "key": [
        "QyNTUxOQAAACAk301w9IHhJiJNiCPSFNZI2JyCcND7TAupRZg9hURWaAAAAKDLnPp+y5z6",
        "-----BEGIN OPENSSH PRIVATE KEY-----",
        "nIJw0PtMC6lFmD2FRFZoAAAAHGdpdEBnaXRsYWIubWFya3VzaGltbWVsLmRlOj8B",
        "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW",
        "-----END OPENSSH PRIVATE KEY-----",
        "AAAECspzatwyFkGca+rIH+gUfiHBHZVbF8wRHvifbnKMReOyTfTXD0geEmIk2II9IU1kjY",
        "fgAAAAtzc2gtZWQyNTUxOQAAACAk301w9IHhJiJNiCPSFNZI2JyCcND7TAupRZg9hURWaA"
        ]
}
let response = client.request("http://gitlab.markushimmel.de:2222/merlin-hunt/the-great-undoing", httpMethod = HttpPost, body = $body)
echo response.status
