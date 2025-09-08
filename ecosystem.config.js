    module.exports = {
      apps : [
        {
          name: "server",
          script: "npm",
          args: "run server",
          interpreter: "none", // Ensures npm handles the script execution
          env: {
            NODE_ENV: "development",
          },
        },
        {
          name: "client",
          script: "npm",
          args: "run client",
          interpreter: "none", // Ensures npm handles the script execution
          env: {
            NODE_ENV: "development",
          },
        },
      ],
    };