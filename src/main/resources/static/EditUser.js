$(async function() {
    editUser();

});
function editUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id : editForm.roles.options[i].value,
                name : "ROLE_" + editForm.roles.options[i].text
            })
        }

        fetch("http://localhost:8080/api/users/" + editForm.id.value, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                name: editForm.name.value,
                surname: editForm.surname.value,
                username: editForm.username.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        }).then(response => {
            if (response.ok){
                $('#editFormCloseButton').click();
                allUsers();
            }else {
                return response
                    .json().then(errorsJson => {
                        const errors = errorsJson.info.split(';')
                        errors.forEach(error => {
                            const [field, message] = error.split(' - ');
                            $(`#${field}EditError`).text(message);
                        })
                })
            }
        })
    })
}