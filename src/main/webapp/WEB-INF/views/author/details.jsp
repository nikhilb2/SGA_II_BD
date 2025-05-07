<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>
			${Writer.firstName} ${Writer.lastName} - Library Management System
		</title>
		<link
			rel="stylesheet"
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
		/>
		<style>
			.details-container {
				background-color: #f8f9fa;
				padding: 2rem;
				border-radius: 0.25rem;
				box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
			}
			.detail-row {
				margin-bottom: 1rem;
			}
			.detail-label {
				font-weight: bold;
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
					<h1>Writer Details</h1>
					<p>View complete information about the Writer.</p>
				</div>
				<div class="col-md-4 text-right">
					<a href="/Writers" class="btn btn-secondary">
						<i class="fas fa-arrow-left"></i> Back to Writers
					</a>
					<a href="/Writers/edit/${Writer.id}" class="btn btn-primary">
						<i class="fas fa-edit"></i> Edit
					</a>
				</div>
			</div>

			<div class="row">
				<div class="col-md-8 offset-md-2">
					<div class="details-container">
						<div class="detail-row">
							<div class="detail-label">ID:</div>
							<div>${Writer.id}</div>
						</div>

						<div class="detail-row">
							<div class="detail-label">Name:</div>
							<div>${Writer.firstName} ${Writer.lastName}</div>
						</div>

						<div class="detail-row">
							<div class="detail-label">Email:</div>
							<div>${Writer.email}</div>
						</div>

						<div class="detail-row">
							<div class="detail-label">Birth Year:</div>
							<div>${Writer.birthYear}</div>
						</div>

						<div class="detail-row">
							<div class="detail-label">Biography:</div>
							<div style="white-space: pre-line">${Writer.biography}</div>
						</div>

						<hr />

						<div class="mt-4">
							<a href="/books/Writer/${Writer.id}" class="btn btn-info">
								<i class="fas fa-book"></i> View Writer's Books
							</a>
							<a href="/Writers/edit/${Writer.id}" class="btn btn-primary">
								<i class="fas fa-edit"></i> Edit Writer
							</a>
						</div>
					</div>
				</div>
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
