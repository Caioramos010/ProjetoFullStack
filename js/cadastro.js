document.querySelector("form").addEventListener("submit", async (event) => {
    event.preventDefault();

    const nome = document.getElementById("nome-cadastro").value;
    const email = document.getElementById("email-cadastro").value;
    const login = document.getElementById("login-cadastro").value;
    const senha = document.getElementById("senha").value;

    const usuario = { nome, email, login, senha };
    console.log("Dados do usuário:", usuario);
    

    try {

        const response = await fetch("http://localhost:8080/colecionador/rest/usuario/cadastrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(usuario),
        });

        if (response.ok) {
            const data = await response.json();
            alert("Cadastro realizado com sucesso!");
            window.location.href = "login.html";
        } else {
            const error = await response.json();
            alert(`Erro ao cadastrar: ${error.mensagem || "Verifique os dados e tente novamente."}`);
        }
    } catch (error) {
        console.error("Erro ao conectar ao servidor:", error);
        alert(error);
    }

});