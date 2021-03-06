header = cell(100,1);
header{1} = '@new file create in matlab gui routine';
for ii = 1:size(attributes, 2)
    if(~isempty(attributes{ii}))
        strinit = strcat({'@attribute '}, attributes{ii}, {' {'});
        str = '';
        listOfValues = data(~cellfun('isempty', data(:,ii)), ii);
        listOfValues = unique(listOfValues);
        numeric = 0;
        for jj = 1:numel(listOfValues)
            str = strcat(str, listOfValues{jj}, {', '});
            numeric = numeric + all(ismember(listOfValues{jj}, '0123456789+-.'));
        end
        strinit = strinit{1};
        str = str{1};
        if numeric >= jj
            str = strcat(strinit(1:end-1), ' numeric');
        else
            str = str(1:end-2); %remove last comma and space
            str = strcat(strinit, str, '}');
        end
        
        header{ii + 1} = str;
    else break
    end
end