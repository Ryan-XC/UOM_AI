function [res]= sumEven()
%SUMEVEN Summary of this function goes here
%   Detailed explanation goes here
%   i = 0:2:100, sum(i)
%   Level1.Q2
    res = 0;
    for i = 1:100
        if mod(i,2)==0
            res = res + i;
        end;
    end;
end

