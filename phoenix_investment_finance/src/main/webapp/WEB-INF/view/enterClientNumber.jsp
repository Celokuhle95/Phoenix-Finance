<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="includes/header.jsp"></jsp:include>
<jsp:include page="includes/navigation.jsp"></jsp:include>
<div class="container">
	<div class="row text-center">
		<h3>Enter your Client Number</h3>
	</div>
	<div class="row">
		<div class="col-md-offset-2 col-md-10">
			<form class="form-inline" action="bond/getPropertyBonds">
				<div class="form-group">
					<label for="clientNum" class="sr-only">Client Number</label> <input
						type="text" class="form-control" id="clientNum"
						name="clientNum">
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
	</div>
</div>
<jsp:include page="includes/footer.jsp"></jsp:include>