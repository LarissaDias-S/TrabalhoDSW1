function mostraForm(tipo)
{
    const formProfissional = document.getElementById("form-profissional");
    const formEmpresa = document.getElementById("form-empresa");

    if (tipo === "profissional") {
        formProfissional.style.display = "block";
        formEmpresa.style.display = "none";
    } else {
        formProfissional.style.display = "none";
        formEmpresa.style.display = "block";
    }
}