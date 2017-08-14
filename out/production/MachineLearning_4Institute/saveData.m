dataToSave = cell(100,1);
dataToSave{1} = '@data';

for ii = 1:size(data(~cellfun('isempty',data(:,1)), 1),1)
    str = '';
    dataRaw = data(ii, ~cellfun('isempty',data(ii,:)));
    for jj = 1:numel(dataRaw)
        str = strcat(str, dataRaw(jj), ',');
    end
    str = str{1};
    str = str(1:end-1);
    dataToSave{ii+1} = str;
end
