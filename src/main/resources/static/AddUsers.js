$(async function() {
    await newUser();
});
async function newUser() {
    await fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.name.substring(5);
                el.value = role.id;
                $('#newUserRoles')[0].appendChild(el);
            })
        })

    const form = document.forms["formNewUser"];

    form.addEventListener('submit', addNewUser)


    function addNewUser(e) {
        e.preventDefault();
        let newUserRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) newUserRoles.push({
                id : form.roles.options[i].value,
                name : "ROLE_" + form.roles.options[i].text
            })
        }
        fetch("http://localhost:8080/api/users", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: form.name.value,
                surname: form.surname.value,
                username: form.username.value,
                password: form.password.value,
                roles: newUserRoles
            })
        }).then((response) => {
            if (response.ok) {
                form.reset();
                allUsers();
                $('#allUsersTable').click();
            }else {
                return response
                    .json().then(errorsJson => {
                        const errors = errorsJson.info.split(';')
                        errors.forEach(error => {
                            const [field, message] = error.split(' - ');
                            $(`#${field}NewError`).text(message);
                        })
                    })
            }
        })
    }

}