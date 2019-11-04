function [ test ] = multiLoop( range )
%MULTILOOP Summary of this function goes here
%   Detailed explanation goes here
%   Level2.Q1 & Q2
    test = zeros(1, range);
    load heart
    mysampler = sampler(data, labels);
    [tr, te] = mysampler.split(1,5);
    for i = 1:range
        model = logreg('iterations', 1, 'learningrate', 0.1).train(tr.data,tr.labels);
        res = model.test(te.data, te.labels);
        test(i) = res.err();
    end
end

