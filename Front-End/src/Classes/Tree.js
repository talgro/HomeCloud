import { Auth } from 'aws-amplify'
const fs = require('fs')
fs.readFile('credentials.txt', 'utf-8', (err, data) => {
  if (err) throw err
  else {
    let credentials = data.split(';')
    Auth.signIn({
      username: credentials[0],
      password: credentials[1]
    })
      .then(data => {
        fs.writeFile('jwt.txt', )
      })
      .catch(err => console.log("failed login data", err))
  }
})
