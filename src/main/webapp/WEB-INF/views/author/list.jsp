<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Writers - Library Management System</title>
		<link
			rel="stylesheet"
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
		/>
		<style>
			.Writer-row:hover {
				background-color: #f8f9fa;
			}
			.table-responsive {
				box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
				border-radius: 0.25rem;
			}
		</style>
	</head>
	<body>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<a class="navbar-brand" href="/">Library Management System</a>
			<button
				class="navbar-toggler"
				type="button"
				data-toggle="collapse"
				data-target="#navbarNav"
			>
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link" href="/">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/books">Books</a>
					</li>
					<li class="nav-item active">
						<a class="nav-link" href="/Writers">Writers</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/books/with-Writers"
							>Books with Writers</a
						>
					</li>
				</ul>
			</div>
		</nav>

		<div class="container mt-4">
			<div class="row mb-4">
				<div class="col-md-8">
					<h1>Writers</h1>
					<p>Manage Writers in your library.</p>
				</div>
				<div class="col-md-4 text-right">
					<a href="/Writers/new" class="btn btn-success">
						<i class="fas fa-plus"></i> Add New Writer
					</a>
				</div>
			</div>

			<!-- Display messages if any -->
			<c:if test="${not empty successMessage}">
				<div
					class="alert alert-success alert-dismissible fade show"
					role="alert"
				>
					${successMessage}
					<button
						type="button"
						class="close"
						data-dismiss="alert"
						aria-label="Close"
					>
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>

			<c:if test="${not empty errorMessage}">
				<div
					class="alert alert-danger alert-dismissible fade show"
					role="alert"
				>
					${errorMessage}
					<button
						type="button"
						class="close"
						data-dismiss="alert"
						aria-label="Close"
					>
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>

			<div class="table-responsive">
				<table class="table table-striped">
					<thead class="thead-dark">
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Email</th>
							<th>Birth Year</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="Writer" items="${Writers}">
							<tr class="Writer-row">
								<td>${Writer.id}</td>
								<td>${Writer.firstName} ${Writer.lastName}</td>
								<td>${Writer.email}</td>
								<td>${Writer.birthYear}</td>
								<td>
									<a href="/Writers/${Writer.id}" class="btn btn-info btn-sm"
										>View</a
									>
									<a
										href="/Writers/edit/${Writer.id}"
										class="btn btn-primary btn-sm"
										>Edit</a
									>
									<a
										href="/books/Writer/${Writer.id}"
										class="btn btn-secondary btn-sm"
										>Books</a
									>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty Writers}">
							<tr>
								<td colspan="5" class="text-center">No Writers found.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>

		<footer class="bg-dark text-white text-center py-3 mt-5">
			<p>Library Management System &copy; 2023</p>
		</footer>

		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	</body>
</html>
