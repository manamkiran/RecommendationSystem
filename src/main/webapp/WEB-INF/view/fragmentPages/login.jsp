<form action="user/login" method="post">
	<div class="form-row align-items-center">
		<div class="col-auto">
			<label class="sr-only" for="inlineFormInput">UserName</label> <input
				type="text" class="form-control mb-2" name="username"
				placeholder="username" required>
		</div>
		<div class="col-auto">
			<label class="sr-only" for="inlineFormInputGroup">Password</label> <input
				type="password" class="form-control mb-2" name="password"
				placeholder="password" required>
		</div>
		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-2">Login</button>
		</div>
		<div class="col-auto">
			<a href="/user/signup" class="btn btn-outline-primary mb-2">SignUp</a>
		</div>
	</div>
</form>