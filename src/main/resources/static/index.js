allUser()

function allUser() {
    $('#table').empty()
    $.getJSON('/admin/list', function (data) {
        $.each(data, function (key, user) {
            let role = []
            for (let i = 0; i < user.roles.length; i++) {
                role.push(user.roles[i].name)
            }
            $('#allUsersTable').append($('<tr>').append(
                    $('<td>').text(user.id),
                    $('<td>').text(user.userName),
                    $('<td>').text(user.lastName),
                    $('<td>').text(user.age),
                    $('<td>').text(user.email),
                    $('<td>').text(role.join(', ')),
                    $('<td>').append(
                        '<button type=\'button\' data-toggle=\'modal\' class=\'btn-info btn\'' +
                        ' data-target=\'#updateUser\' data-user-id=\'' + user.id + '\'>Update</button>'),
                    $('<td>').append(
                        '<button type=\'button\' data-toggle=\'modal\' class=\'btn btn-danger\'' +
                        ' data-target=\'#deleteUser\' data-user-id=\'' + user.id + '\'>Delete</button>'),
                ),
            )
        })
    })
    $('[href="#admin"]').on('show.bs.tab', () => {
        location.reload()
    })
}

$('#updateUser').on('show.bs.modal', function (e) {
    let userId = $(e.relatedTarget).data('user-id')
    let modal = $(this)
    $.ajax({
        url: `/admin/get?id=${userId}`,
        type: 'GET',
        dataType: 'json'
    }).done(function (user) {
        modal.find('#viewId').val(userId)
        modal.find('#updateName').val(user.userName)
        modal.find('#updateLastName').val(user.lastName)
        modal.find('#updateAge').val(user.age)
        modal.find('#updateEmail').val(user.email)
        modal.find('#updatePassword').val('')

        $.getJSON('admin/roles', function (roles) {
            let roleUser = modal.find('#updateRoles').empty()
            roles.forEach(function (role) {
                roleUser.append(new Option(role.name, role.name))
            })
            if (user.roles) {
                user.roles.forEach(function (userWithRole) {
                    roleUser.find(`option[value="${userWithRole.name}"]`).prop('selected', true)
                })
            }
            roleUser.trigger("change")
        })
    })
})

$('#buttonUpdateSubmit').on('click', (e) => {
    e.preventDefault()
    const updateUser = {
        id: $('#viewId').val(),
        userName: $('#updateName').val(),
        lastName: $('#updateLastName').val(),
        age: $('#updateAge').val(),
        email: $('#updateEmail').val(),
        password: $('#updatePassword').val(),
        roles: $('#updateRoles').val()
    }
    let jsonData = JSON.stringify(updateUser)

    $.ajax({
        url: '/admin',
        type: 'PUT',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonData,
    })

    $('#updateUser').modal('hide')
    allUser()
    location.reload()
})

$('#deleteUser').on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data('user-id')

    $.ajax({
        url: `/admin/get?id=${userId}`,
        type: 'GET',
        dataType: 'json'
    }).done((message) => {
        let user = JSON.parse(JSON.stringify(message))

        $('#delId').empty().val(user.id)
        $('#delUserName').empty().val(user.userName)
        $('#delLastName').empty().val(user.lastName)
        $('#delAge').empty().val(user.age)
        $('#delEmail').empty().val(user.email)
        let roles = ''
        for (let i = 0; i < user.roles.length; i++) {
            roles += '<option value="' + user.roles[i].name + '">' + user.roles[i].name + '</option>'
        }
        $('#delRoles').empty().html(roles)

        $('#buttonDeleteSubmit').on('click', (e) => {
            e.preventDefault()

            $.ajax({
                url: `/admin?id=${userId}`,
                type: 'DELETE',
                dataType: 'text'
            }).done(() => {
                $('#deleteUser').modal('hide')
                location.reload()
            })
        })
    })
})

function getActiveUser() {
    $.ajax({
        url: '/account',
        type: 'GET',
        dataType: 'json'
    }).done((m) => {
        let user = JSON.parse(JSON.stringify(m))
        let role = []
        for (let i = 0; i < user.roles.length; i++) {
            role.push(user.roles[i])
        }
        $('#user-table tbody').empty().append(
            $('<tr>').append(
                $('<td>').text(user.id),
                $('<td>').text(user.userName),
                $('<td>').text(user.lastName),
                $('<td>').text(user.age),
                $('<td>').text(user.email),
                $('<td>').text(role.join(', ')),
            ))
    })
}

$('[href="users"]').on('show.bs.tab', () => {
    $('#AllTabContent').hide()
    getActiveUser()
})

$('[href="#new"]').on('show.bs.tab', () => {
    $.getJSON('admin/roles', function (roles) {
        $(() => {
            $('#newUserName').empty().val('')
            $('#newLastName').empty().val('')
            $('#newAge').empty().val('')
            $('#newEmail').empty().val('')
            $('#newPassword').empty().val('')
            $('#newRoles').empty().val()
            $.each(roles, function (key, role) {
                $('#newRoles').append(
                    $('<option>').text(role.name).attr('value', role.name),
                )
            })
        })
    })
})
$('#buttonNewUser').on('click', (e) => {
    e.preventDefault();
    const newUser = {
        userName: $('#newUserName').val(),
        lastName: $('#newLastName').val(),
        age: $('#newAge').val(),
        email: $('#newEmail').val(),
        password: $('#newPassword').val(),
        roles: $('#newRoles').val(),
    };
    let jsonNew = JSON.stringify(newUser);

    $.ajax({
        url: '/admin',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: jsonNew
    })

    $('#Tab a[href="#admin"]').tab('show');
    location.reload()
});