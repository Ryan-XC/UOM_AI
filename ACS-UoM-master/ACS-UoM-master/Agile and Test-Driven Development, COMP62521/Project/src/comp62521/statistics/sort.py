def sort_search(x, y, author):
    if (author.lower() in x.split()[-1][0:len(author)].lower() and author.lower() in y.split()[-1][
                                                                                     0:len(author)].lower()):
        if (x.split()[-1] == y.split()[-1]):
            return cmp(x, y)
        else:
            return cmp(x.split()[-1], y.split()[-1])
    elif (author.lower() in x.split()[-1][0:len(author)].lower() and author.lower() not in y.split()[-1][
                                                                                           0:len(author)].lower()):
        return -1
    elif (author.lower() in y.split()[-1][0:len(author)].lower() and author.lower() not in x.split()[-1][
                                                                                           0:len(author)].lower()):
        return 1
    elif (author.lower() in x.split()[0][0:len(author)].lower() and author.lower() in y.split()[0][
                                                                                      0:len(author)].lower()):
        if (x.split()[0] == y.split()[0]):
            return cmp(x.split()[-1], y.split()[-1])
        else:
            return cmp(x.split()[0], y.split()[0])
    elif (author.lower() in x.split()[0][0:len(author)].lower() and author.lower() not in y.split()[0][
                                                                                          0:len(author)].lower()):
        return -1
    elif (author.lower() in y.split()[0][0:len(author)].lower() and author.lower() not in x.split()[0][
                                                                                          0:len(author)].lower()):
        return 1

    elif (author.lower() in x.split()[1][0:len(author)].lower() and author.lower() in y.split()[1][
                                                                                      0:len(author)].lower()):
        if (x.split()[1] == y.split()[1]):
            return cmp(x.split()[-1], y.split()[-1])
        else:
            return cmp(x.split()[1], y.split()[1])
    elif (author.lower() in x.split()[1][0:len(author)].lower() and author.lower() not in y.split()[1][
                                                                                          0:len(author)].lower()):
        return -1
    elif (author.lower() in y.split()[1][0:len(author)].lower() and author.lower() not in x.split()[1][
                                                                                          0:len(author)].lower()):
        return 1
    elif (author.lower() in x.split()[-1].lower() and author.lower() in y.split()[-1].lower()):
        if (x.split()[-1] == y.split()[-1]):
            return cmp(x, y)
        else:
            return cmp(x.split()[-1], y.split()[-1])
    elif (author.lower() in x.split()[-1].lower() and author.lower() not in y.split()[-1].lower()):
        return -1
    elif (author.lower() in y.split()[-1].lower() and author.lower() not in x.split()[-1].lower()):
        return 1
    else:
        return cmp(x, y)
