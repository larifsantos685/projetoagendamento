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
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            alert("Login efetuado!");
            localStorage.setItem('username', username);
            sessionCookie = document.cookie;
            isLoggedIn = true;
            

            // Redireciona para a pagina de tarefas
            window.location.href = "tasks.html";
        } else {
            alert("Erro ao logar");
        }
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("Erro ao tentar fazer login.");
    });

}

function logout(){
    fetch('http://localhost:8080/users/logout', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if (response.ok) {
            alert("Log out efetuado!");
            localStorage.removeItem('username');
            document.cookie = "sessionCookie=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            isLoggedIn = false;
            
            // Redireciona para a pagina de login
            window.location.href = "login.html";
        } else {
            alert("Erro ao logar");
        }
    })
    .catch(error => {
        console.error("Erro na requisição:", error);
        alert("Erro ao tentar fazer login.");
    });
}

function getTasks() {

    fetch('http://localhost:8080/tasks', {
        method: 'GET',
        credentials: 'include'
    })
        .then(res => res.json())
        .then(tasks => {
            const list = document.getElementById('taskList');
            list.innerHTML = '';
            tasks.forEach(task => {
                const card = document.createElement('div');
                card.className = 'card'
                const formattedDate = task.dataCriada;

                card.innerHTML = `
                    <p class="heading">
                        ${task.descricao}
                    </p>
                    <p>${formattedDate}</p>
                    <p>${task.titulo}</p>
                    <p>Urgência: ${task.grauUrgencia}</p> 
                    <p>ID: ${task.id}</p> 
                    <div class="card-actions">
                        <button onclick="populateEditForm('${task.id}', '${task.titulo}', '${task.descricao}', '${task.grauUrgencia}', '${task.dataCriada}')">Editar</button>
                        <button onclick="deleteTask('${task.id}')">Concluir Tarefa</button>
                    </div>
                `;
                list.appendChild(card);
            });
        }).catch(() => alert("Você precisa estar logado!"));
}

function createTask() {
    const title = document.getElementById('newTaskTitle').value;
    const description = document.getElementById('newTaskDescription').value;
    const grauUrgencia = document.getElementById('newTaskGrauUrgencia').value;
    const newTask = { titulo: title, descricao: description, grauUrgencia: grauUrgencia };

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

// Deletar
function deleteTask(taskId) {
    if (!confirm(`Tem certeza que deseja deletar a tarefa com ID ${taskId}?`)) {
        return; // Cancela se o user não confirmar
    }

    fetch(`http://localhost:8080/tasks/${taskId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        alert(`Tarefa ${taskId} deletada com sucesso!`);
        getTasks(); // Recarrega a lista de tarefas após a deleção
    })
    .catch(error => {
        console.error('Erro ao deletar tarefa:', error);
        alert('Erro ao deletar tarefa. Verifique o console.');
    });
}

//Popular a nova task para editar
function populateEditForm(id, titulo, descricao, grauUrgencia, dataCriada) {
    document.getElementById('newTaskTitle').value = titulo;
    document.getElementById('newTaskDescription').value = descricao;
    document.getElementById('newTaskGrauUrgencia').value = grauUrgencia;
    
    // id indo pro input hidden = <input type="hidden" id="editTaskId">
    const editTaskIdInput = document.getElementById('editTaskId');
    if (editTaskIdInput) {
        editTaskIdInput.value = id;
    } else {
        console.warn("Input com ID 'editTaskId' não encontrado. Adicione-o ao seu HTML para que a edição funcione corretamente.");
    }

    // texto do botão de "Criar" para "Atualizar"
    const createTaskButton = document.querySelector('#taskSection button[onclick="createTask()"]');
    if (createTaskButton) {
        createTaskButton.textContent = 'Atualizar Tarefa';
        createTaskButton.setAttribute('onclick', 'saveTask()'); // Muda para uma nova função saveTask
    } else {
        console.warn("Botão 'Criar Tarefa' não encontrado ou seletor incorreto.");
    }

    alert(`Editando tarefa: ${titulo}. Preencha os campos e clique em 'Atualizar Tarefa'.`);
}

// Editar / atualizar
function saveTask() {
    const editTaskIdInput = document.getElementById('editTaskId');
    const taskId = editTaskIdInput ? editTaskIdInput.value : '';

    const editTaskDataCriadaInput = document.getElementById('editTaskDataCriada'); // NOVO
    const originalDataCriada = editTaskDataCriadaInput ? editTaskDataCriadaInput.value : ''; // NOVO

    const titleInput = document.getElementById('newTaskTitle');
    const descriptionInput = document.getElementById('newTaskDescription');
    const urgencySelect = document.getElementById('newTaskGrauUrgencia');

    const taskData = {
        titulo: titleInput.value,
        descricao: descriptionInput.value,
        grauUrgencia: urgencySelect.value,
    };

    let url = "http://localhost:8080/tasks";
    let method = 'POST';

    if (taskId) { 
        method = 'PUT';
        taskData.id = parseInt(taskId); // adiciona o ID ao corpo da requisição para edicao
        taskData.dataCriada = originalDataCriada; // Inclui a data original no corpo
    } else { // Se nao tem ID, é uma criação (POST)
        taskData.dataCriada = new Date().toISOString().split('T')[0]; // Formato "YYYY-MM-DD"
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(taskData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.indexOf("application/json") !== -1) {
            return response.json();
        } else {
            return response.text(); // retorna texto se não for JSON
        }
    })
    .then(() => {
        alert(`Tarefa ${taskId ? 'atualizada' : 'criada'} com sucesso!`);
        // limpa o formulário e reseta os IDs escondidos
        titleInput.value = '';
        descriptionInput.value = '';
        urgencySelect.value = 'BAIXO';
        if (editTaskIdInput) {
            editTaskIdInput.value = ''; // limpa o ID da tarefa em edição
        }
        if (editTaskDataCriadaInput) { // NOVO
            editTaskDataCriadaInput.value = ''; // limpa a data original em edição
        }

        // Reseta o botão de volta para "Criar Tarefa"
        const createTaskButton = document.querySelector('#taskSection button[onclick="saveTask()"]');
        if (createTaskButton) {
            createTaskButton.textContent = 'Criar Tarefa';
            createTaskButton.setAttribute('onclick', 'createTask()');
        }
        getTasks();
    })
    .catch(error => {
        console.error(`Erro ao ${taskId ? 'atualizar' : 'criar'} tarefa:`, error);
        alert(`Erro ao ${taskId ? 'atualizar' : 'criar'} tarefa. Verifique o console.`);
    });
    
}