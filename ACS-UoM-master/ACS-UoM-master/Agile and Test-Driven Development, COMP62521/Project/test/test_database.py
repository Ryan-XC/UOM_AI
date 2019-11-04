from os import path
import unittest

from comp62521.database import database
from comp62521.statistics.sort import sort_search
from comp62521.views import format_data, showAverages, showCoAuthors, showSearchAuthor, showStatisticsMenu, \
    showPublicationSummary, showAuthorSummary


class TestDatabase(unittest.TestCase):
    def setUp(self):
        dir, _ = path.split(__file__)
        self.data_dir = path.join(dir, "..", "data")

    def test_read(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        self.assertEqual(len(db.publications), 1)

    def test_read_invalid_xml(self):
        db = database.Database()
        self.assertFalse(db.read(path.join(self.data_dir, "invalid_xml_file.xml")))

    def test_read_missing_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "missing_year.xml")))
        self.assertEqual(len(db.publications), 0)

    def test_read_missing_title(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "missing_title.xml")))
        # publications with missing titles should be added
        self.assertEqual(len(db.publications), 1)

    def test_get_average_authors_per_publication(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-1.xml")))
        _, data = db.get_average_authors_per_publication(database.Stat.MEAN)
        self.assertAlmostEqual(data[0], 2.3, places=1)
        _, data = db.get_average_authors_per_publication(database.Stat.MEDIAN)
        self.assertAlmostEqual(data[0], 2, places=1)
        _, data = db.get_average_authors_per_publication(database.Stat.MODE)
        self.assertEqual(data[0], [2])

    def test_get_average_publications_per_author(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-2.xml")))
        _, data = db.get_average_publications_per_author(database.Stat.MEAN)
        self.assertAlmostEqual(data[0], 1.5, places=1)
        _, data = db.get_average_publications_per_author(database.Stat.MEDIAN)
        self.assertAlmostEqual(data[0], 1.5, places=1)
        _, data = db.get_average_publications_per_author(database.Stat.MODE)
        self.assertEqual(data[0], [0, 1, 2, 3])

    def test_get_average_publications_in_a_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-3.xml")))
        _, data = db.get_average_publications_in_a_year(database.Stat.MEAN)
        self.assertAlmostEqual(data[0], 2.5, places=1)
        _, data = db.get_average_publications_in_a_year(database.Stat.MEDIAN)
        self.assertAlmostEqual(data[0], 3, places=1)
        _, data = db.get_average_publications_in_a_year(database.Stat.MODE)
        self.assertEqual(data[0], [3])

    def test_get_average_authors_in_a_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-4.xml")))
        _, data = db.get_average_authors_in_a_year(database.Stat.MEAN)
        self.assertAlmostEqual(data[0], 2.8, places=1)
        _, data = db.get_average_authors_in_a_year(database.Stat.MEDIAN)
        self.assertAlmostEqual(data[0], 3, places=1)
        _, data = db.get_average_authors_in_a_year(database.Stat.MODE)
        self.assertEqual(data[0], [0, 2, 4, 5])
        # additional test for union of authors
        self.assertEqual(data[-1], [0, 2, 4, 5])

    def test_get_publication_summary(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_publication_summary()
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data[0]), 6,
                         "incorrect number of columns in data")
        self.assertEqual(len(data), 2,
                         "incorrect number of rows in data")
        self.assertEqual(data[0][1], 1,
                         "incorrect number of publications for conference papers")
        self.assertEqual(data[1][1], 2,
                         "incorrect number of authors for conference papers")

    def test_get_average_authors_per_publication_by_author(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_average_authors_per_publication_by_author(database.Stat.MEAN)
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data), 3,
                         "incorrect average of number of conference papers")
        self.assertEqual(data[0][1], 1.5,
                         "incorrect mean journals for author1")
        self.assertEqual(data[1][1], 2,
                         "incorrect mean journals for author2")
        self.assertEqual(data[2][1], 1,
                         "incorrect mean journals for author3")

    def test_get_publications_by_author(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_publications_by_author()
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data), 2,
                         "incorrect number of authors")
        self.assertEqual(data[0][-1], 1,
                         "incorrect total")

    def test_get_average_publications_per_author_by_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_average_publications_per_author_by_year(database.Stat.MEAN)
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data), 1,
                         "incorrect number of rows")
        self.assertEqual(data[0][0], 9999,
                         "incorrect year in result")

    def test_get_average_authors_per_publication_by_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_average_authors_per_publication_by_year(1)
        self.assertEqual(data, [[9999, 2, 0, 0, 0, 2]])

    def test_get_publications_by_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_publications_by_year()
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data), 1,
                         "incorrect number of rows")
        self.assertEqual(data[0][0], 9999,
                         "incorrect year in result")

    def test_get_author_totals_by_year(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_author_totals_by_year()
        self.assertEqual(len(header), len(data[0]),
                         "header and data column size doesn't match")
        self.assertEqual(len(data), 1,
                         "incorrect number of rows")
        self.assertEqual(data[0][0], 9999,
                         "incorrect year in result")
        self.assertEqual(data[0][1], 2,
                         "incorrect number of authors in result")

    def test_get_coauthor_data(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_coauthor_data(9998,10000,4)
        self.assertEqual(data, [[u'AUTHOR1 (1)', u'AUTHOR2 (1)'], [u'AUTHOR2 (1)', u'AUTHOR1 (1)']])

    def test_get_network_data(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_network_data()
        self.assertEqual(data, set([(0, 1)]))

    def test__get_collaborations(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        data = db._get_collaborations(0, 1)
        self.assertEqual(data, {0: 1, 1: 1})

    def test_get_publication_summary_average(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_publication_summary_average(2)
        self.assertEqual(data, [['Mode authors per publication', [1], [], [], [], [1]], ['Mode publications per author', [1.0], [0.0], [0.0], [0.0], [1.0]]])

    """Test cases for sprint 2 TDD approach"""
    def test_get_author_times(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'AUTHOR1', 1, 0, 0], [u'AUTHOR2', 0, 1, 0]])

    def test_get_author_times2(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-1.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'AUTHOR1', 1, 1, 0], [u'AUTHOR2', 0, 1, 0], [u'AUTHOR3', 0, 1, 0], [u'AUTHOR4', 2, 0, 0]])

    def test_get_author_times3(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-2.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'AUTHOR1', 2, 0, 1], [u'AUTHOR3', 0, 0, 0], [u'AUTHOR4', 0, 2, 0], [u'AUTHOR2', 0, 0, 1]])

    def test_get_author_times4(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-3.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'AUTHOR', 0, 0, 9], [u'AUTHOR1', 0, 0, 1]])

    def test_get_author_times5(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-4.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'AUTHOR1', 1, 0, 2], [u'AUTHOR2', 0, 1, 0], [u'AUTHOR3', 2, 1, 0], [u'AUTHOR4', 0, 2, 0], [u'AUTHOR6', 1, 0, 0], [u'AUTHOR7', 0, 0, 1], [u'AUTHOR8', 0, 0, 2]])

    def test_get_author_times6(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_author_times()
        self.assertEqual(data, [[u'author1', 1, 0, 1], [u'author2', 0, 1, 0], [u'author3', 0, 0, 1]])

    def test_get_information_by_author(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_information_by_author('author1')
        self.assertEqual(data, [['author1', 2, 2, 0, 0, 0, 1, 1, 0, 1]])

    def test_get_information_by_author_1(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_information_by_author('author2')
        self.assertEqual(data, [['author2', 1, 1, 0, 0, 0, 1, 0, 1, 0]])

    def test_get_information_by_author_2(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_information_by_author('author3')
        self.assertEqual(data, [['author3', 1, 1, 0, 0, 0, 0, 0, 0, 1]])

    def test_get_information_by_author_3(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "publications_small_sample.xml")))
        header, data = db.get_information_by_author('Sean Bechhofer')
        self.assertEqual(data, [['Sean Bechhofer', 1, 1, 0, 0, 0, 3, 1, 0, 0]])

    def test_get_information_by_author_4(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "publications_small_sample.xml")))
        header, data = db.get_information_by_author('Stefano Ceri')
        self.assertEqual(data, [['Stefano Ceri', 2, 0, 0, 1, 1, 2, 2, 0, 0]])

    def test_get_information_by_author_5(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "missing_title.xml")))
        x = None
        data = db.get_information_by_author(x)
        self.assertEqual(data, ['Result    not    found'])

    def test_get_coauthor_details(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_coauthor_details('author1')
        self.assertEqual(data, (u'author2', 1))

    def test_get_information_author(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_information_author('author1')
        print data
        self.assertEqual(data, [['conference papers', 2, 1, 0, 1], ['journal articles', 0, 0, 0, 0], ['books', 0, 0, 0, 0], ['book chapters', 0, 0, 0, 0], ['overall', 2, 1, 0, 1], ['Number of Co-Author', 1]])
        data = db.get_information_author('wadawdawdw')
        self.assertEqual(data, ['Result    not    found'])

    def test_get_author_times_type1(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "publications_small_sample.xml")))
        header, data = db.get_author_times_type('Carole A. Goble')
        self.assertEqual(data, [[u'Sean Bechhofer', 0, 0, 0], [u'Yeliz Yesilada', 0, 0, 0], [u'Bernard Horan', 0, 0, 0], [u'Carole A. Goble', 0, 0, 0], [u'Simon Harper', 0, 0, 0], [u'Robert Stevens', 0, 0, 0], [u'Stefano Ceri', 0, 0, 0], [u'Piero Fraternali', 0, 0, 0], [u'Raghu Ramakrishnan', 0, 0, 0], [u'Erich Bornberg-Bauer', 0, 0, 0], [u'Norman W. Paton', 0, 0, 0], [u'Duncan Hull', 0, 0, 0], [u'Katy Wolstencroft', 0, 0, 0], [u'Rodrigo Lopez', 0, 0, 0]])

    def test_get_author_times_type2(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        header, data = db.get_author_times_type('author1')
        self.assertEqual(data, [[u'author1', 0, 0, 0], [u'author2', 0, 0, 0], [u'author3', 0, 0, 0]])

    def test_get_author_times_type3(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        header, data = db.get_author_times_type('author2')
        self.assertEqual(data, [[u'AUTHOR1', 0, 0, 0], [u'AUTHOR2', 0, 0, 0]])

    def test_get_author_search(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "publications_small_sample.xml")))
        header, data = db.get_author_search('Carole A. Goble')
        self.assertEqual(data,[u'Carole A. Goble'])
        data = db.get_author_search('zzzzzzz')
        self.assertEqual(data,['Result    not    found'])

        ############View Test##################
    def test_format_data(self):
        temp = [1.331]
        res = format_data(temp)
        list = [13.2592, 14.6701]
        listres = format_data(list)
        self.assertTrue(1.33, res)
        self.assertTrue([13.26, 14.67], listres)

    def test_showAverage(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        self.assertEqual("OK", showAverages("Y", db), "FAILED")

    def test_menu(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        self.assertEqual("OK", showStatisticsMenu("Y", db), "FAILED")

    def test_summary(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))
        self.assertEqual("OK", showPublicationSummary("publication_summary", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("publication_author", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("publication_year", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_year", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_number", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_number_journal", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_number_conference", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_number_book", "Y", db), "FAILED")
        self.assertEqual("OK", showPublicationSummary("author_number_chapter", "Y", db), "FAILED")

    def test_showCoAuthor(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "simple.xml")))

    def test_sort_search(self):
        res = sort_search("Alice Sam", "Brian Sam", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Brian Sam", "Alice Sammer", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Alice Sam", "Alice Sam", "sam")
        self.assertEqual(0, res)
        res = sort_search("Alice Sam", "Alice Sam Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Alice Sam Brian", "Alice Sam", "sam")
        self.assertEqual(1, res)

        res = sort_search("Sam Alice", "Sam Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Sam Brian", "Sammer Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Sam Alice", "Sam Alice", "sam")
        self.assertEqual(0, res)
        res = sort_search("Sam Alice", "Alice Sam Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Alice Sam Brian", "Alice Sam", "sam")
        self.assertEqual(1, res)

        res = sort_search("Brian Sam Alice", "Alice Sam Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Alice Sam Brian", "Brian Sammer Alice", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Brian Sammer Alice", "Brian Sammer Alice", "sam")
        self.assertEqual(0, res)
        res = sort_search("Alice Esam", "Brian Sam Alice", "sam")
        self.assertEqual(1, res)
        res = sort_search("Brian Sam Alice", "Alice Esam", "sam")
        self.assertEqual(-1, res)     
        
        res = sort_search("Alice Esam", "Alice Brian", "sam")
        self.assertEqual(-1, res)
        res = sort_search("Alice Brian", "Alice Esam", "sam")
        self.assertEqual(1, res)
        res = sort_search("Alice Esam", "Alice Esam", "sam")
        self.assertEqual(0, res)
        res = sort_search("Blice Esam", "Alice Esam", "sam")
        self.assertEqual(1, res)
        res = sort_search("Alice Esam", "Alice Esamer", "sam")
        self.assertEqual(-1, res)

        res = sort_search("Alice Brian", "Alice Brian", "sam")
        self.assertEqual(0, res)
        res = sort_search("Blice Brian", "Alice Brian", "sam")
        self.assertEqual(1, res)
        res = sort_search("Alice Brian", "Blice Brian", "sam")
        self.assertEqual(-1, res)


    def test_degrees_separation(self):
        db = database.Database()
        # self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        # data = db.get_degrees_separation('author1','author3',[])
        # self.assertEqual(data, -1)
        # data = db.get_degrees_separation('author1','author2',[])
        # self.assertEqual(data, 0)
        self.assertTrue(db.read(path.join(self.data_dir, "sprint-2-acceptance-4.xml")))
        data = db.get_degrees_separation('AUTHOR4','AUTHOR6',[])
        self.assertEqual(data, 2)

        # we have to pass this test

    def test_degrees_separation_header(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "three-authors-and-three-publications.xml")))
        data = db.get_degrees_separation_header('author1','author3')
        self.assertEqual(data,(('Author1','Author2','Degrees of separation'),['author1','author3', 'X']))
 
        
    def test_degrees_separation_graph(self):
        db = database.Database()
        self.assertTrue(db.read(path.join(self.data_dir, "test_double_link.xml")))
        data = db.get_degrees_graph('authorc','authord')
        self.assertEqual(data, [['authorc','authorb','authord'],['authorc','authorg','authord']])

if __name__ == '__main__':
    unittest.main()
