<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Books with Writers - Library Management System</title>
		<link
			rel="stylesheet"
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
		/>
		<style>
			.book-row:hover {
				background-color: #f8f9fa;
			}
			.table-responsive {
				box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
				border-radius: 0.25rem;
			}
			.special-heading {
				border-bottom: 2px solid #007bff;
				padding-bottom: 0.5rem;
				margin-bottom: 1.5rem;
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
					<li class="nav-item">
						<a class="nav-link" href="/Writers">Writers</a>
					</li>
					<li class="nav-item active">
						<a class="nav-link" href="/books/with-Writers"
							>Books with Writers</a
						>
					</li>
				</ul>
			</div>
		</nav>

		<div class="container mt-4">
			<div class="row mb-4">
				<div class="col-12">
					<h1 class="special-heading">Books with Writers (Inner Join)</h1>
					<p class="lead">
						This page demonstrates the use of a custom query to perform an inner
						join between books and Writers.
					</p>
				</div>
			</div>

			<div class="table-responsive">
				<table class="table table-striped">
					<thead class="thead-dark">
						<tr>
							<th>Book ID</th>
							<th>Book Title</th>
							<th>ISBN</th>
							<th>Genre</th>
							<th>Writer ID</th>
							<th>Writer Name</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="bookWithWriter" items="${booksWithWriters}">
							<tr class="book-row">
								<td>${bookWithWriter.bookId}</td>
								<td>${bookWithWriter.bookTitle}</td>
								<td>${bookWithWriter.isbn}</td>
								<td>${bookWithWriter.genre}</td>
								<td>${bookWithWriter.WriterId}</td>
								<td>${bookWithWriter.WriterName}</td>
								<td>
									<a
										href="/books/${bookWithWriter.bookId}"
										class="btn btn-info btn-sm"
										>View Book</a
									>
									<a
										href="/Writers/${bookWithWriter.WriterId}"
										class="btn btn-primary btn-sm"
										>View Writer</a
									>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty booksWithWriters}">
							<tr>
								<td colspan="7" class="text-center">No data found.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>

			<div class="card mt-4">
				<div class="card-header bg-info text-white">
					<h5 class="mb-0">Technical Details</h5>
				</div>
				<div class="card-body">
					<h6>Query Used:</h6>
					<pre><code>@Query("SELECT b, a FROM Book b INNER JOIN b.Writer a")
List&lt;Object[]&gt; findBooksWithWriters();</code></pre>

					<h6 class="mt-3">Implementation:</h6>
					<p>
						This query performs an inner join between the Book and Writer
						entities using JPA's JPQL (Java Persistence Query Language). The
						result is processed in the service layer to create a list of maps
						containing both book and Writer information.
					</p>
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
