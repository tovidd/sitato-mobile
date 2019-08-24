<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class jasaService extends Model
{
    protected $table= 'jasa_service';
    protected $primaryKey= 'id_jasa_service';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'kode_jasa_service',
        'nama_jasa_service',
        'harga_jasa_service'
    ];
}
