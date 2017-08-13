header = cell(100,1);
header{1} = '@new file create in matlab gui routine';
for ii = 1:size(attributes, 2)
    if(~isempty(attributes{ii}))
        str = strcat({'@attribute '}, attributes{ii}, {' {'});
        listOfValues = data(~cellfun('isempty', data(1:end,ii)));
        for jj = 1:numel(listOfValues)
            str = strcat(str, listOfValues{jj}, {', '});
        end
        str = str{1}(1:end-2); %remove last comma and space
        str = strcat(str, '}');
        
        header{ii + 1} = str;
    else break
    end
end