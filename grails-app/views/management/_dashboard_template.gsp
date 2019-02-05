<meta name="layout" content="header"/>
<div class="container">
    <form class="form" to-url="/auth/register-app" onsubmit="return false">
        <div class="form-group">
            <label>App name</label>
            <input type="text" class="form-control" placeholder="Your app name">
            <small class="form-text text-muted"></small>
        </div>
        <div class="form-group">
            <label>App URL</label>
            <input type="text" class="form-control" placeholder="App URL">
            <small id="emailHelp" class="form-text text-muted"></small>
        </div>
        <button type="submit" class="btn btn-primary btnSubmit">Submit</button>
    </form>
</div>
<asset:javascript src="dashboard.js"/>