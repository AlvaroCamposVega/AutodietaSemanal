function mostrar(oscurecer, formulario) {
	
	document.getElementById(oscurecer).style.display = "initial";
	document.getElementById(formulario).style.display = "initial";
}

function eliminar(boton) {
	
	var fila = boton.parentElement.parentElement.children;
	var id = fila[3].firstElementChild.href;
	id = id.substring(id.search("id=[0-9]+")).substring(3);
	var usuario = fila[0].innerHTML;
	
	setFormulario(id, usuario);
	mostrar('oscurecer', 'formulario');
}

function setFormulario(id, usuario) {
	
	var capaFormulario = document.getElementById('formulario');
	var idInput = capaFormulario.children[2].firstElementChild;
	idInput.value = id;
	var textoUsuario = capaFormulario.children[1];
	textoUsuario.innerText = usuario;
}

function ocultar(oscurecer, formulario) {
	
	document.getElementById(oscurecer).style.display = "none";
	document.getElementById(formulario).style.display = "none";
}