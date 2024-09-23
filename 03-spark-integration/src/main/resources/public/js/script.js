function confirmDeleteStudent(id, name) {  
    if (confirm('Confirmar a exclusão do aluno [' + name + '] (ID ' + id + ')')) {  
        location.href = '/student/delete/' + id;
    }
}

function onLoad() {
    if(document.getElementById("msg").value != ""){
      alert(document.getElementById("msg").value);
    }
}