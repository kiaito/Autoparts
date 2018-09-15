<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="WEB-INF/headerTemplate.jsp" %>

            <div class='main-container'>
                <div id="slideshow-wrapper" class='relative'>
                    <div class='search-form col-xs-5'>
                        <h2>Search for parts</h2>
                        <form class="form-horizontal" action="/action_page.php">
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="customerID" placeholder="Customer ID#">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="part" placeholder="Search for parts">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <button type="submit" class="btn btn-primary submit-btn">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="slideshow">
                        <img src="https://images.pexels.com/photos/924675/pexels-photo-924675.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"></img>
                        <img src="https://images.pexels.com/photos/159275/macro-focus-cogwheel-gear-159275.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"></img>
                        <img src="https://images.pexels.com/photos/191738/pexels-photo-191738.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"></img>
                    </div>
                    <div style="text-align: center">
                        <div id="circle1" class="circle"></div>
                        <div id="circle2" class="circle"></div>
                        <div id="circle3" class="circle"></div>
                    </div>
                </div>

            </div>

            <hr/>
            <div class="row text-center feature-imgs">
                <h2>Feature Items</h2>
                <div class="col-sm-4">
                    <div class="thumbnail">
                        <img src="https://www.autozone.com/images/MEDIA_ProductCatalog/m9880016_deals-v47.png" alt="Paris">
                        <p><strong>Full Synthetic Oil</strong></p>
                        <button class="btn">Add to cart</button>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="thumbnail">
                        <img src="https://www.autozone.com/images/MEDIA_ProductCatalog/m3490247_prd-Alternator.jpg" alt="New York">
                        <p><strong>Alternator</strong></p>
                        <button class="btn">Add to cart</button>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="thumbnail">
                        <img src="https://www.autozone.com/images/MEDIA_ProductCatalog/m3490609_prd-Idler-Pulley.jpg" alt="San Francisco">
                        <p><strong>Pulley</strong></p>
                        <button class="btn">Add to cart</button>
                    </div>
                </div>
            </div>
        <%@ include file="WEB-INF/footerTemplate.jsp" %>
    </body>
</html>