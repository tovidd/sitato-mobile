<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class kendaraan extends Model
{
    protected $table= 'kendaraan';
    protected $primaryKey= 'id_kendaraan';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'no_plat_kendaraan',
        'merek_kendaraan',
        'jenis_kendaraan'
    ];
}
