/**
 * Created by Martin on 16/11/2015.
 */
$(function () {
    //TODO UI enhancement.
    var table = document.getElementsByTagName('table');
    var table$ = $(table);
    var ele = document.getElementsByTagName('th');
    var ele$ = $(ele[0]);
    var size = ele.length;

    ele$.each(function () {
        var th = $(this);
        var thIndex = th.index();
        var inverse = false;

        th.click(function () {
            table$.find('td').filter(function () {
                return $(this).index() === thIndex;
            }).sortElements(function (a, b) {
                var name1 = $.text([a]).toUpperCase().split(" ");
                var name2 = $.text([b]).toUpperCase().split(" ");
                return name1[name1.length - 1] > name2[name2.length - 1] ?
                    inverse ? -1 : 1 : inverse ? 1 : -1;

            }, function () {
                return this.parentNode;
            });
            inverse = !inverse;
        });
    });
    //Sorting with second criterion
    var thIndex;
    var inverse = false;
    for (var i = 1; i < size; i++) {
        var number$ = $(ele[i]);
        number$.each(function () {
            var th = $(this);
            th.click(function () {
                thIndex = th.index();
                var rows = $("#table1 tbody tr").detach().get();
                rows.sort(sortbyTwo);
                $("#table1 tbody").append(rows);
                inverse = !inverse;
            });
        });
    }
    function sortbyTwo(row1, row2) {
        var v1, v2, r;
        if(!inverse) {
            v1 = $(row1).find("td:eq(" + thIndex + ")").text();
            v2 = $(row2).find("td:eq(" + thIndex + ")").text();
            r = v1 - v2;
            if (r === 0) {
                // we have a tie in column 1 values, compare column 2 instead
                v1 = $(row1).find("td:eq(0)").text().toUpperCase().split(" ");
                v2 = $(row2).find("td:eq(0)").text().toUpperCase().split(" ");
                if (v1[v1.length - 1] < v2[v2.length - 1]) {
                    r = -1;
                } else if (v1[v1.length - 1] > v2[v2.length - 1]) {
                    r = 1;
                } else {
                    r = 0;
                }
            }
            return r;
        }else{
            v1 = $(row1).find("td:eq(" + thIndex + ")").text();
            v2 = $(row2).find("td:eq(" + thIndex + ")").text();
            r = v2 - v1;
            if (r === 0) {
                // we have a tie in column 1 values, compare column 2 instead
                v1 = $(row1).find("td:eq(0)").text().toUpperCase().split(" ");
                v2 = $(row2).find("td:eq(0)").text().toUpperCase().split(" ");
                if (v1[v1.length - 1] < v2[v2.length - 1]) {
                    r = -1;
                } else if (v1[v1.length - 1] > v2[v2.length - 1]) {
                    r = 1;
                } else {
                    r = 0;
                }
            }
            return r;
        }
    }
});
