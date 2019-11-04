from comp62521 import app
from database import database
from flask import (render_template, request)


def format_data(data):
    fmt = "%.2f"
    result = []
    for item in data:
        if type(item) is list:
            result.append(", ".join([(fmt % i).rstrip('0').rstrip('.') for i in item]))
        else:
            result.append((fmt % item).rstrip('0').rstrip('.'))
    return result


@app.route("/averages")
def showAverages(debug=None, db=None):
    if debug is None:
        dataset = app.config['DATASET']
        db = app.config['DATABASE']
    else:
        dataset = "test.xml"
    args = {"dataset": dataset, "id": "averages"}
    args['title'] = "Averaged Data"
    tables = []
    headers = ["Average", "Conference Paper", "Journal", "Book", "Book Chapter", "All Publications"]
    averages = [database.Stat.MEAN, database.Stat.MEDIAN, database.Stat.MODE]
    tables.append({
        "id": 1,
        "title": "Average Authors per Publication",
        "header": headers,
        "rows": [
            [database.Stat.STR[i]]
            + format_data(db.get_average_authors_per_publication(i)[1])
            for i in averages]})
    tables.append({
        "id": 2,
        "title": "Average Publications per Author",
        "header": headers,
        "rows": [
            [database.Stat.STR[i]]
            + format_data(db.get_average_publications_per_author(i)[1])
            for i in averages]})
    tables.append({
        "id": 3,
        "title": "Average Publications in a Year",
        "header": headers,
        "rows": [
            [database.Stat.STR[i]]
            + format_data(db.get_average_publications_in_a_year(i)[1])
            for i in averages]})
    tables.append({
        "id": 4,
        "title": "Average Authors in a Year",
        "header": headers,
        "rows": [
            [database.Stat.STR[i]]
            + format_data(db.get_average_authors_in_a_year(i)[1])
            for i in averages]})

    args['tables'] = tables
    if debug is not None:
        return "OK"
    return render_template("averages.html", args=args)


@app.route("/coauthors")
def showCoAuthors():
    dataset = app.config['DATASET']
    db = app.config['DATABASE']
    PUB_TYPES = ["Conference Papers", "Journals", "Books", "Book Chapters", "All Publications"]
    args = {"dataset": dataset, "id": "coauthors"}
    args["title"] = "Co-Authors"

    start_year = db.min_year
    if "start_year" in request.args:
        start_year = int(request.args.get("start_year"))

    end_year = db.max_year
    if "end_year" in request.args:
        end_year = int(request.args.get("end_year"))

    pub_type = 4
    if "pub_type" in request.args:
        pub_type = int(request.args.get("pub_type"))

    args["data"] = db.get_coauthor_data(start_year, end_year, pub_type)
    args["start_year"] = start_year
    args["end_year"] = end_year
    args["pub_type"] = pub_type
    args["min_year"] = db.min_year
    args["max_year"] = db.max_year
    args["start_year"] = start_year
    args["end_year"] = end_year
    args["pub_str"] = PUB_TYPES[pub_type]

    return render_template("coauthors.html", args=args)

@app.route("/degrees")
def showDegreesSeparation():
    dataset = app.config['DATASET']
    db = app.config['DATABASE']
    args = {"dataset": dataset, "id": "degrees", "title": "Degrees of separation"}
    args["author"]= db.get_all_authors()
    author1 = request.args.get("author1")
    author2 = request.args.get("author2")
    if author1 is None or author2 is None or author2 == "" or author1 == "":
        args["data"]=[]
        return render_template("degreeseparation.html",args=args)
    else:
        args["data"]=db.get_degrees_separation_header(author1,author2)
        return render_template("degreeseparation.html",args=args)

@app.route("/graph")
def showGraph():
    dataset = app.config['DATASET']
    db = app.config['DATABASE']
    args = {"dataset": dataset, "id": "graph", "title": "Graph Degrees of separation"}
    args["author"]= db.get_all_authors()
    author1 = request.args.get("author1")
    author2 = request.args.get("author2")
    if author1 is None or author2 is None or author2 == "" or author1 == "":
        args["datas"]=[]
        args["authors"]=[]
        return render_template("graph.html",args=args)
    else:
        args["datas"]=db.get_degrees_graph(author1,author2)
        args["authors"]=[author1,author2]
        return render_template("graph.html",args=args)

@app.route("/searchauthor")
def showSearchAuthor():
    """Returns Search Page"""
    dataset = app.config['DATASET']
    db = app.config['DATABASE']
    args = {"dataset": dataset, "id": "searchauthor", "title": "Search-Authors"}

    author_name = request.args.get("author_name")
    good = request.args.get("good")
    if good is None:
        if author_name is None:
            args["data"] = []
        else:
            args["data"] = db.get_author_search(author_name)
        return render_template("searchauthor.html", args=args)
    else:
        if author_name is None:
           args["data"] = []
        else:
            args["data"] = db.get_information_by_author(author_name)
        return render_template("statistics_details.html", args=args)   

@app.route("/")
def showStatisticsMenu(debug=None, db=None):
    if debug is None:
        dataset = app.config['DATASET']
        args = {"dataset": dataset}
    else:
        dataset = "test.xml"
    if debug is not None:
        return "OK"
    return render_template('statistics.html', args=args)


@app.route("/statisticsdetails/<status>")
def showPublicationSummary(status, debug=None, db=None):
    if debug is None:
        dataset = app.config['DATASET']
        db = app.config['DATABASE']
    else:
        dataset = "test.xml"
    args = {"dataset": dataset, "id": status}

    if (status == "publication_summary"):
        args["title"] = "Publication Summary"
        args["data"] = db.get_publication_summary()

    if (status == "publication_author"):
        args["title"] = "Author Publication"
        args["data"] = db.get_publications_by_author()
        # print(args["data"][0]) Testing.

    if (status == "publication_year"):
        args["title"] = "Publication by Year"
        args["data"] = db.get_publications_by_year()

    if (status == "author_year"):
        args["title"] = "Author by Year"
        args["data"] = db.get_author_totals_by_year()

    if (status == "author_number"):
        args["title"] = "Author Appears in Paper"
        args["data"] = db.get_author_times()

    if (status == "author_number_journal"):
        args["title"] = "Author Appears in journal article"
        args["data"] = db.get_author_times_type(1)        

    if (status == "author_number_conference"):
        args["title"] = "Author Appears in conference paper"
        args["data"] = db.get_author_times_type(0)  

    if (status == "author_number_book"):
        args["title"] = "Author Appears in book"
        args["data"] = db.get_author_times_type(2)  

    if (status == "author_number_chapter"):
        args["title"] = "Author Appears in book chapter"
        args["data"] = db.get_author_times_type(3)

    if debug is not None:
        return "OK"

    return render_template('statistics_details.html', args=args)


@app.route("/author")
def showAuthorSummary():
    dataset = app.config['DATASET']
    db = app.config['DATABASE']
    author_name = request.args.get("author_name")
    args = {"dataset": dataset, "id": "author", "title": "Information about " + author_name}
    args["data"] = db.get_information_author(author_name)
    return render_template("statistics_details.html", args=args)
