let sessionCookie = "";
let isLoggedIn = false;

function register() {
    const username = document.getElementById('registerUsername').value;
    const password = document.getElementById('registerPassword').value;

    fetch('http://localhost:8080/users/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }), // Cria um usuário padrão
    })
        .then(response => {
            if (response.ok) {
                alert("Usuário cadastrado com sucesso!");
                isLoggedIn = true;
            } else {
                alert("Erro ao cadastrar usuário");
            }
        });
}
function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                alert("Login efetuado!");
                sessionCookie = document.cookie;
                document.getElementById('taskSection').style.display = 'block';
                isLoggedIn = true; 
            } else {
                alert("Erro ao logar");
            }
        });

}

function getTasks() {

    if (!isLoggedIn) {
        alert("Você precisa estar logado!");
        return;
    }
    fetch('http://localhost:8080/tasks', {
        method: 'GET',
        credentials: 'include'
    })
        .then(res => res.json())
        .then(tasks => {
            const list = document.getElementById('taskList');
            list.innerHTML = '';
            tasks.forEach(task => {
                const item = document.createElement('li');
                item.textContent = `#${task.id}: ${task.descricao}`;
                list.appendChild(item);
            });
        }).catch(() => alert("Você precisa estar logado!"));
}

function createTask() {
    const title = document.getElementById('newTaskTitle').value;
    const newTask = { id: 0, descricao: title };

    fetch('http://localhost:8080/tasks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTask),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                alert("Task criada com sucesso!");
                getTasks();
            } else {
                alert("Erro ao criar task");
            }
        });
}
